import { Component, OnInit, OnDestroy } from '@angular/core';
import { OrderService } from '../service/order.service';
import { AuthService } from '../service/auth.service';
import { Router, NavigationStart } from '@angular/router';
import { Order } from '../model/order';
import { Subscription } from 'rxjs';
import { OrderItem } from '../model/orderItem';
import { CartService } from '../service/cart.service';
import { forkJoin, of } from 'rxjs';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit, OnDestroy {
  public orders: Order[] = [];
  public orderItems: OrderItem[] = [];
  private currentUserId: string | null = null;
  totalAmount: number = 0;
  public paid: boolean = false;
  public paymentSuccess: boolean = false;
  private routerSubscription!: Subscription;
  public selectedPaymentMethod: string = '';
  public userName: string = '';
  public userEmail: string = '';
  public userAddress: string = '';
  public phoneNumber: string = '';
  public currentOrderId: number = 0;
  public orderId: number = 0;
  processingOrder: boolean = false;

  orderStatus: string = 'Check-out-in-progress'; 
  public isProcessing: boolean = false; 

  constructor(
    private orderService: OrderService,
    private authService: AuthService,
    private cartService: CartService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.currentUserId = this.authService.getCurrentUserId();
    this.loadOrders();
    this.subscribeToRouterEvents();
    window.addEventListener('beforeunload', this.handleBeforeUnload);
  }

  ngOnDestroy(): void {
    this.routerSubscription.unsubscribe();
    window.removeEventListener('beforeunload', this.handleBeforeUnload);
  }

  private handleBeforeUnload = (event: BeforeUnloadEvent): void => {
    if (!this.paid && this.orders.length > 0) {
      const confirmationMessage = 'You have items in your checkout. If you leave this page, those items will be deleted.';
      event.returnValue = confirmationMessage; // For most browsers
     // return confirmationMessage; // For some older browsers
    }
  }

  private loadOrders(): void {
    this.orderService.getOrdersByUserId(this.currentUserId).subscribe({
      next: (data: Order[]) => {
        this.orders = data.filter(order => 
          order.userId === Number(this.currentUserId) && 
          order.status === "Check-out-in-progress"
        );
        this.calculateTotalAmount();

        if (this.orders.length > 0 && this.orders[0].id !== undefined) {
          this.currentOrderId = this.orders[0].id;
        } else {
          console.error('No valid orders found or order ID is undefined');
          this.currentOrderId = 0; 
        }

        this.orderStatus = this.orders.length > 0 ? this.orders[0].status : 'No orders found';
      },
      error: (error) => {
        console.error('Error fetching orders:', error);
      }
    });
  }

  private subscribeToRouterEvents(): void {
    this.routerSubscription = this.router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        this.confirmNavigation(event);
      }
    });
  }

  private confirmNavigation(event: NavigationStart): void {
    if (!this.paid && this.orders.length > 0) {
      const confirmation = confirm('You have items in your checkout. If you leave this page, those items will be deleted. Do you want to proceed?');
      if (confirmation) {
        this.removeCurrentOrders();
      } else {
        // Prevent navigation
        this.router.navigateByUrl(this.router.url);
      }
    }
  }

  private removeCurrentOrders(): void {
    this.orders.forEach(order => {
      if (order.id) {
        this.orderService.removeOrder(order.id).subscribe({
          next: () => {
            console.log(`Order with ID ${order.id} deleted successfully.`);
            // Optionally: Remove from local orders array if needed
            this.orders = this.orders.filter(o => o.id !== order.id);
          },
          error: (error) => {
            console.error('Error deleting order:', error);
          }
        });
      }
    });
  }

  confirmOrder(): void {
    // Logic for confirming the order goes here
  }

  private calculateTotalAmount(): void {
    this.totalAmount = this.orders.reduce((sum, order) => sum + parseFloat(order.totalAmount), 0);
  }

  simulateEwalletPayment(): void {
    this.isProcessing = true;
    this.paymentSuccess = false;

    console.log('Processing payment for the following orders:', this.orders);

    this.processingOrder = true;

    setTimeout(() => {
      this.paid = true;
      this.isProcessing = false; 
      this.processingOrder = false;
      this.paymentSuccess = true;

      const updateObservables = this.orders.map(order => {
        if (order.id !== undefined) {
          order.status = 'To Ship'; 
          return this.orderService.putUpdate(order.id, order);
        } else {
          console.error('Order ID is undefined for the order:', order);
          return of(null); 
        }
      });

      forkJoin(updateObservables).subscribe({
        next: (responses) => {
          responses.forEach((response, index) => {
            if (response) {
              console.log(`Order ID ${this.orders[index].id} status updated to 'To Ship':`, response);
            }
          });
          this.removeItemsFromCart();
        },
        error: (error) => {
          console.error('Error updating orders:', error);
        },
      });
    }, 2000);
  }

  private removeItemsFromCart(): void {
    const removeObservables = this.orders.map(order => {
      if (order.orderId) {
        return this.cartService.removeItemFromCart(order.orderId);
      } else {
        console.error('Order ID is undefined for the order:', order);
        return of(null); 
      }
    });

    forkJoin(removeObservables).subscribe({
      next: responses => {
        responses.forEach((response, index) => {
          if (response) {
            console.log(`Order Item with ID ${this.orders[index].orderId} removed from the cart.`);
          }
        });
      },
      error: (error) => {
        console.error('Error removing order item from cart:', error);
      },
    });
  }

  closePaymentModal(): void {
    this.paymentSuccess = false;
  }

  printInvoice(): void {
    window.print();
  }

  updatePaymentButton(): void {
    this.paymentSuccess = false;
    this.paid = false;
  }
}
