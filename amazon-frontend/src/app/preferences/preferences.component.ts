import { Component } from '@angular/core';
import { PreferencesService } from './preferences.service';
import { Color, Preferences } from './preferences.model';

@Component({
  selector: 'app-preferences',
  templateUrl: './preferences.component.html',
  styleUrl: './preferences.component.css'
})
export class PreferencesComponent {

  preferences?: Preferences;
  colors = Object.values(Color);

  constructor(private preferencesService: PreferencesService) {
  }

  ngOnInit() {
    this.loadPreferences();
  }

  loadPreferences() {
    this.preferencesService.findPreferences();
    this.preferencesService.preferencesSubject.subscribe(res => {
      this.preferences = res;
    });
  }

  onCheckboxChange(event: Event) {
    const checkbox = event.target as HTMLInputElement;
    switch (checkbox.name) {
      case 'ai':
        this.preferences!.ai = checkbox.checked;
        break;
      case 'prime':
        this.preferences!.prime = checkbox.checked;
        break;
      default:
        break;
    }
    this.preferencesService.updatePreferences(this.preferences!);

  }

  onDropdownChange(event: Event) {
    const select = event.target as HTMLSelectElement;
    switch (select.name) {
      case 'color':
        this.preferences!.color = Color[select.value as keyof typeof Color];
        break;
      default:
        break;
    }
    this.preferencesService.updatePreferences(this.preferences!);
  }

  onRadioChange(event: Event) {
    const radio = event.target as HTMLInputElement;
    switch (radio.name) {
      case 'color':
        this.preferences!.color = radio.value as Color;
        break;
      default:
        break;
    }
    this.preferencesService.updatePreferences(this.preferences!);
  }

  onTextChange(event: Event) {
    const input = event.target as HTMLInputElement;
    this.preferences!.text_value = input.value;
    this.preferencesService.updatePreferences(this.preferences!);
  }

  onColorInputChange(event: Event) {
    const input = event.target as HTMLInputElement;
    this.preferences!.hex_color = input.value;
    this.preferencesService.updatePreferences(this.preferences!);
  }

}
