import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-ticket',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './ticket.component.html',
  styleUrl: './ticket.component.css'
})
export class TicketComponent {

  public content2D: any[] = [
    { time: 'Before 12PM', standard: '55.000đ', vip: '65.000đ', couple: '75.000đ', standard1: '90.000đ', vip1: '95.000đ', couple1: '105.000đ' },
    { time: 'From 12PM to before 5PM', standard: '50.000đ', vip: '70.000đ', couple: '80.000đ', standard1: '70.000đ', vip1: '75.000đ', couple1: '155.000đ' },
    { time: 'From 5PM to before 11:00PM', standard: '45.000đ', vip: '85.000đ', couple: '65.000đ', standard1: '80.000đ', vip1: '125.000đ', couple1: '135.000đ' },
    { time: 'From 11:00PM', standard: '55.000đ', vip: '75.000đ', couple: '90.000đ', standard1: '85.000đ', vip1: '105.000đ', couple1: '115.000đ' },
  ]
  public content3D: any[] = [
    { time: 'Before 12PM', standard: '65.000đ', vip: '70.000đ', couple: '80.000đ', standard1: '95.000đ', vip1: '105.000đ', couple1: '125.000đ' },
    { time: 'From 12PM to before 5PM', standard: '55.000đ', vip: '75.000đ', couple: '85.000đ', standard1: '75.000đ', vip1: '95.000đ', couple1: '165.000đ' },
    { time: 'From 5PM to before 11:00PM', standard: '55.000đ', vip: '90.000đ', couple: '75.000đ', standard1: '85.000đ', vip1: '135.000đ', couple1: '145.000đ' },
    { time: 'From 11:00PM', standard: '75.000đ', vip: '85.000đ', couple: '95.000đ', standard1: '95.000đ', vip1: '115.000đ', couple1: '125.000đ' },
  ]
}
