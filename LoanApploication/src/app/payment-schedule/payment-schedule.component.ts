import { Component, OnInit } from '@angular/core';
import { HttpService } from '../http.service';

@Component({
  selector: 'app-payment-schedule',
  templateUrl: './payment-schedule.component.html',
  styleUrls: ['./payment-schedule.component.css']
})
export class PaymentScheduleComponent implements OnInit {

  Details:any[]=[];
  p:number=1;
  num:number=0;
  constructor(private service:HttpService) { }

  ngOnInit(): void 
  {
    this.getScheduleDetails();
  }

  getScheduleDetails(){
    this.service.getSchedule().subscribe((result:any)=>{
       this.Details=result
    })
  }

  onClick(id:any){
    let popup = document.getElementById('loan')
    popup?.classList.add("open-popup")
    this.num=id
  }
  
  cancelPayment(){
    let popup = document.getElementById("loan")
    popup?.classList.remove("open-popup")
    //window.location.reload();
  }
  closeCancel(){
    let popup = document.getElementById("loan")
    popup?.classList.remove("open-popup")
    alert("Payment is canceled")
    window.location.reload();
     }
  SetPayment(status:any){
      let popup = document.getElementById("loan")
      popup?.classList.remove("open-popup")
        this.service.payment(status,this.num).subscribe((result:any)=>
      {
        console.log(result)
     })
     window.location.reload();
       }
}
