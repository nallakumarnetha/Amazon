import { Component } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { CommonService } from '../common/common.service';

@Component({
  selector: 'app-demo-mode',
  templateUrl: './demo-mode.component.html',
  styleUrl: './demo-mode.component.css'
})
export class DemoModeComponent {
  constructor(private messageService: MessageService, private commonService: CommonService
    , private confirmationService: ConfirmationService
  ) {

  }

  dumpData() {
    this.commonService.dumpData();
    this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Data dumped successfully!' });
  }

  clearData() {
    this.commonService.clearData();
    this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Data cleared successfully!' });
  }

  confirmClearData() {
    this.confirmationService.confirm({
      message: 'Are you sure you want to <b>clear all data</b>?',
      header: 'Clear Data',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Clear',
      rejectLabel: 'Cancel',
      acceptButtonStyleClass: 'accept-btn',
      rejectButtonStyleClass: 'reject-btn',
      accept: () => {
        this.clearData();
      }
    });
  }


  confirmDumpData() {
    this.confirmationService.confirm({
      message: `Do you want to <b>Dump Sample Data</b>?`,
      header: 'Dump Data',
      icon: 'pi pi-info-circle',
      acceptLabel: 'Dump',
      rejectLabel: 'Cancel',
      acceptButtonStyleClass: 'accept-btn bg-color',
      rejectButtonStyleClass: 'reject-btn',

      accept: () => {
        this.dumpData();
      }
    });
  }

}
