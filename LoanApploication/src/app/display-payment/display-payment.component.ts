import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route } from '@angular/router';
import { HttpService } from '../http.service';

@Component({
  selector: 'app-display-payment',
  templateUrl: './display-payment.component.html',
  styleUrls: ['./display-payment.component.css']
})
export class DisplayPaymentComponent implements OnInit {

  Details:any[]=[];
  num:number=0;
  constructor(private service:HttpService,private route:ActivatedRoute) { }

  ngOnInit(): void 
  {
    this.getEachSchedule();
  }

  getEachSchedule(){
   this.route.paramMap.subscribe((param)=>{
    this.service.getEachSchedule(param.get('customerName')).subscribe((result:any)=>{
      this.Details=result;
    })
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

