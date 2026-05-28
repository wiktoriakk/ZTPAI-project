import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ProductService, Product } from '../../services/product.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product-list.html',
  styleUrl: './product-list.css',
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  loading = true;
  error: string | null = null;
  isAdmin = false;

  newProduct = { name: '', price: 0, description: '' };
  creating = false;
  createError: string | null = null;
  createSuccess = false;

  constructor(
    private productService: ProductService,
    private authService: AuthService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.isAdmin = this.authService.getRole() === 'ADMIN';
    this.loadProducts();
  }

  loadProducts(): void {
    this.loading = true;
    this.error = null;
    this.productService.getProducts().subscribe({
      next: (data) => {
        this.products = data;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.error = err.status === 401 ? 'Unauthorized – please log in.' : 'Failed to load products.';
        this.loading = false;
        this.cdr.detectChanges();
      },
    });
  }

  onCreateProduct(): void {
    this.creating = true;
    this.createError = null;
    this.createSuccess = false;

    this.productService.createProduct(this.newProduct as Product).subscribe({
      next: (created) => {
        this.products = [...this.products, created];
        this.newProduct = { name: '', price: 0, description: '' };
        this.creating = false;
        this.createSuccess = true;
        this.cdr.detectChanges();
        setTimeout(() => {
          this.createSuccess = false;
          this.cdr.detectChanges();
        }, 3000);
      },
      error: (err) => {
        this.createError =
          err.status === 403 ? 'Access denied – ADMIN role required.' : 'Failed to create product.';
        this.creating = false;
        this.cdr.detectChanges();
      },
    });
  }

  get avgPrice(): number {
    if (!this.products.length) return 0;
    return this.products.reduce((sum, p) => sum + p.price, 0) / this.products.length;
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
