import { Component } from '@angular/core';
import { PromotionCoreService } from '../../../services/promotion/promotion-core.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-promotion',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './promotion.component.html',
  styleUrl: './promotion.component.css'
})
export class PromotionComponent {

  public promotionCore: any[] = [];

  constructor(private promotionService: PromotionCoreService) {
    this.promotionCore = this.promotionService.getPromotion();
  }
}
