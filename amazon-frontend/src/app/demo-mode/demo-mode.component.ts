import { Component } from '@angular/core';
import { MessageService } from 'primeng/api';
import { CommonService } from '../common/common.service';

@Component({
  selector: 'app-demo-mode',
  templateUrl: './demo-mode.component.html',
  styleUrl: './demo-mode.component.css'
})
export class DemoModeComponent {
  constructor(private messageService: MessageService, private commonService: CommonService) {

  }

  dumpData() {
    this.commonService.dumpData();
    this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Data dumped successfully!' });
  }

  clearData() {
    this.commonService.clearData();
    this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Data cleared successfully!' });
  }

}
