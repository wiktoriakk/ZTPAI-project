import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ProductService, Product } from '../../services/product.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './product-list.html',
  styleUrl: './product-list.css',
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  loading = true;
  error: string | null = null;

  constructor(
    private productService: ProductService,
    private authService: AuthService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    if (!this.authService.isLoggedIn()) {
      this.loading = false;
      this.router.navigate(['/login']);
      return;
    }

    this.productService.getProducts().subscribe({
      next: (data) => {
        this.products = data;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error fetching products:', err);
        this.error =
          err.status === 401
            ? 'Unauthorized – please log in.'
            : 'Failed to load products.';
        this.loading = false;
        this.cdr.detectChanges();
      },
    });
  }
}
