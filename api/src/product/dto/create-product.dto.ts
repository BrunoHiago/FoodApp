import { Category } from 'src/utils/enums';

export class CreateProductDto {
  name: string;
  description: string;
  price: number;
  category: Category;
  restaurantId: string;
}
