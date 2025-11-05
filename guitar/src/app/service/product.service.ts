import { Injectable } from '@angular/core';
import { BaseHttpService } from './base-http.service';
import { BehaviorSubject, Observable } from 'rxjs';
import { Product } from '../model/product';
import { HttpClient } from '@angular/common/http';
import { ProductCategory } from '../model/product-category';

@Injectable({
  providedIn: 'root'
})
export class ProductService extends BaseHttpService{

  constructor(protected override http: HttpClient) { 
    super(http, '/api/product')
  }
 }

 //our javascript classes, implement alraedy autoscanning
 //dependency injection
 // normally we create a new object, pero dito hindi muna kailangan kasi angular (y)