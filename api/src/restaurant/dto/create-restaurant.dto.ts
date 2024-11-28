import { Category } from 'src/utils/enums';

export class CreateRestaurantDto {
  name: string;
  description: string;
  address: string;
  cep: string;
  phone: string;
  photo: string;
  rating: number;
  category: Category;
}
