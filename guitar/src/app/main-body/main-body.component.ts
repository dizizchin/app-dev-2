import { Component, OnInit } from '@angular/core';
import { ProductService } from '../service/product.service';
import { AuthService } from '../service/auth.service';
import { ProductCategory } from '../model/product-category';

@Component({
  selector: 'app-main-body',
  templateUrl: './main-body.component.html',
  styleUrls: ['./main-body.component.css']
})
export class MainBodyComponent implements OnInit {
  public productsCategory: ProductCategory[] = [];
  public userEmail: string | null = null; // Property to hold the user's email

  constructor(private productService: ProductService, private authService: AuthService) {}

  ngOnInit(): void {
    console.log("ngOnInit called");
    
    const status = this.authService.isLoggedIn() ? 'user' : 'guest';
    console.log(`Current login status: ${status}`); // Log login status

    // Get and output the user's email if logged in
    if (this.authService.isLoggedIn()) {
      this.userEmail = this.authService.getCurrentUserEmail();
      console.log(`Current user email: ${this.userEmail}`); // Log user email
      //refresh window
      
    } else {
      console.log("No user is logged in.");
    }

    this.productService.getData().subscribe(data => {
      this.productsCategory = data;
    });
  }
}
