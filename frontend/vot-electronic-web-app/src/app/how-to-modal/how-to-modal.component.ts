import { Component, TemplateRef } from '@angular/core';
import { BsModalService, BsModalRef} from 'ngx-bootstrap';

@Component({
  selector: 'app-how-to-modal',
  templateUrl: './how-to-modal.component.html',
  styleUrls: ['./how-to-modal.component.css']
})
export class HowToModalComponent  {
  modalRef: BsModalRef;
  constructor(private modalService: BsModalService) {}

  openModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }

}
