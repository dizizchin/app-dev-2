import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../service/user.service';
import { User } from '../model/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  signupForm!: FormGroup;
  submitted = false;

  constructor(private fb: FormBuilder,
     private userService: UserService,
     private router: Router
    ) {}

  ngOnInit() {
    this.signupForm = this.fb.group({
      userEmail: ['', [Validators.required, Validators.email]],
      userName: ['', [Validators.required]], // Update this line
      userPassword: ['', [Validators.required]],
      firstName: ['', [Validators.required]], // Ensure these match your HTML
      middleName: [''],
      lastName: ['', [Validators.required]],
      dateOfBirth: ['', [Validators.required]],
      phoneNumber: ['', [Validators.required]],
      userAddress: ['', [Validators.required]],
      userGender: ['', [Validators.required]],
  });
  
  }

  onSignup() {
    this.submitted = true; // Set to true when the form is submitted
    if (this.signupForm.valid) {
      const user: User = { ...this.signupForm.value };

      this.userService.add(user).subscribe(
        response => {
          console.log('User created successfully:', response);
          console.log('User ID set:', response.userId); // Assuming backend returns the user with its ID
          // Redirect to login page
          this.router.navigate(['/signin']);
        },
        error => {
          console.error('Error creating user:', error);
        }
      );
    } else {
      console.log('Form is invalid');
    }
  }
}
