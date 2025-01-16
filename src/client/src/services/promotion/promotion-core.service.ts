import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
})
export class PromotionCoreService {

    constructor() { }

    public getPromotion(): any {
        return [
            { image: './assets/images/promotion-01.webp', date: '01/08/2025', title: 'RA MẮT BỎNG MIX VỊ - GIÁ KHÔNG ĐỔI' },
            { image: './assets/images/promotion-02.webp', date: '19/07/2025', title: 'ƯU ĐÃI ĐẶC BIỆT THỨ 2 - THỨ 3 - THỨ 4 HÀNG THÁNG' },
            { image: './assets/images/promotion-03.webp', date: '029/08/2025', title: 'BẢNG GIÁ BỎNG, NƯỚC MỚI NHẤT 2024' },
            { image: './assets/images/promotion-04.webp', date: '05/010/2025', title: 'THẺ U22 ƯU ĐÃI GIÁ VÉ CHO HỌC SINH, SINH VIÊN 55.000Đ/VÉ 2D' },
            { image: './assets/images/promotion-05.webp', date: '03/04/2025', title: 'SIÊU ƯU ĐÃI “PHIM THẬT HAY - COMBO THẬT ĐÃ” CHÍNH THỨC TRỞ LẠI' },
            { image: './assets/images/promotion-06.webp', date: '09/07/2025', title: 'XEM PHIM HAY HƯỞNG NGAY ƯU ĐÃI “ĂN THẢ GA – CHƠI CỰC ĐÃ”' },
        ];
    }
}