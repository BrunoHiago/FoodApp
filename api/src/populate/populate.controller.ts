import { Controller, Get } from '@nestjs/common';
import { PopulateService } from './populate.service';

@Controller('populate')
export class PopulateController {
  constructor(private readonly populateService: PopulateService) {}

  @Get()
  async populate() {
    await this.populateService.populateDatabase();
  }
}
