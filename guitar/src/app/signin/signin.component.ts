import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../service/user.service';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { OrderItem } from '../model/orderItem';
import { CartService } from '../service/cart.service';
import { switchMap } from 'rxjs/operators';
import { of, forkJoin } from 'rxjs';
import { Product } from '../model/product';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {
  signinForm!: FormGroup;
  errorMessage: string = '';
  private currentCurrentUserId: string | null = "";
  private currentUsername: string | null = "";

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router,
    private authService: AuthService,
    private cartService: CartService
  ) {}

  ngOnInit() {
    this.signinForm = this.fb.group({
      userEmail: ['', [Validators.required, Validators.email]],
      userPassword: ['', [Validators.required]],
    });
  }

  onSignin() {
    this.authService.signIn(this.signinForm.value.userEmail, this.signinForm.value.userPassword)
      .subscribe(
        () => {
          this.router.navigate(['']).then(() => {
            // Force reload after navigation
            this.currentCurrentUserId = this.authService.getCurrentUserId();
            this.currentUsername = this.authService.getCurrentUsername();
            this.updateCusIDofOrdID(); // Call the update function after successful sign-in
            window.location.reload();
          });
        },
        error => {
          this.errorMessage = error.message;
        }
      );
  }

  updateCusIDofOrdID() {
    this.cartService.getData().pipe(
      switchMap((items: OrderItem[]) => {
        const guestItems = items.filter(item => item.userId === 0);

        // Retrieve current user's cart items
        const currentUserItems = items.filter(item => item.userId === Number(this.currentCurrentUserId));

        const updateObservables = guestItems.map(item => {
          // Check if the product already exists in the current user's cart
          const existsInCurrentCart = currentUserItems.some(userItem => userItem.productId === item.productId);

          if (!existsInCurrentCart) {
            const updatedItem: OrderItem = {
              ...item,
              userId: Number(this.currentCurrentUserId),
              userName: this.currentUsername || ''
            };
            return this.cartService.putUpdate(item.id, updatedItem); // Update each item
          } else {
            console.log(`Item with productId ${item.productId} already exists in the current user's cart. Skipping update.`);
            return of(null); // Return a no-op observable for existing items
          }
        }).filter(obs => obs !== null); // Filter out null observables

        return forkJoin(updateObservables); // Wait for all updates to complete
      })
    ).subscribe(
      responses => {
        console.log('Updated user IDs and names for guest items:', responses);
      },
      error => {
        console.error('Error updating user IDs and names for guest items:', error);
      }
    );
  }
}
