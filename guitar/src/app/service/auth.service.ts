import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private loggedInSubject = new BehaviorSubject<boolean>(this.checkLoggedInStatus());
  public loggedIn$ = this.loggedInSubject.asObservable();

  constructor(private userService: UserService) {}

  signIn(email: string, password: string): Observable<any> {
    return this.userService.findAll().pipe(
      map(users => {
        const user = users.find((user: any) =>
          user.userEmail === email && user.userPassword === password
        );

        if (user) {
          localStorage.setItem('isLoggedIn', 'true');
          localStorage.setItem('currentUserEmail', user.userEmail); // Store the user email
          localStorage.setItem('currentUsername', user.userUsername); // Store the username
          localStorage.setItem('currentUserId', user.id); // Store the user id // dapat user id to pero id lang muna--raf

          this.loggedInSubject.next(true);
          return { success: true };
        } else {
          throw new Error('Invalid email or password');
        }
      }),
      catchError(error => {
        console.error('Error during sign-in:', error);
        throw error;
      })
    );
}


  logout(): void {
    localStorage.removeItem('isLoggedIn');
    localStorage.removeItem('currentUserEmail'); // Remove the user email on logout
    this.loggedInSubject.next(false);
  }

  isLoggedIn(): boolean {
    return this.loggedInSubject.getValue();
  }

  private checkLoggedInStatus(): boolean {
    return localStorage.getItem('isLoggedIn') === 'true';
  }


  getCurrentUserEmail(): string | null {
    return localStorage.getItem('currentUserEmail');
  }
  getCurrentUsername(): string | null {
    return localStorage.getItem('currentUsername');
}
getCurrentUserId(): string | null {
  return localStorage.getItem('currentUserId');
}

}
