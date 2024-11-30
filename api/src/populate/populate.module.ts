import { Module } from '@nestjs/common';
import { PopulateService } from './populate.service';
import { PopulateController } from './populate.controller';
import { RestaurantModule } from 'src/restaurant/restaurant.module';
import { ProductModule } from 'src/product/product.module';

@Module({
  imports: [RestaurantModule, ProductModule],
  controllers: [PopulateController],
  providers: [PopulateService],
  exports: [PopulateService],
})
export class PopulateModule {}
