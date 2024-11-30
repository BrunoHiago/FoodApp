import { Injectable } from '@nestjs/common';
import { ProductService } from 'src/product/product.service';
import { RestaurantService } from 'src/restaurant/restaurant.service';
import { CreateProductDto } from 'src/product/dto/create-product.dto';
import { data } from 'src/utils/data';
import { Category } from 'src/utils/enums';

@Injectable()
export class PopulateService {
  constructor(
    private readonly restaurantService: RestaurantService,
    private readonly productService: ProductService,
  ) {}
  async populateDatabase(): Promise<void> {
    const jsonData = data;

    for (const restaurantData of jsonData) {
      const categoryValue =
        Category[
          restaurantData.category.toUpperCase() as keyof typeof Category
        ];
      if (!categoryValue) {
        throw new Error(`Categoria inválida: ${restaurantData.category}`);
      }
      // Criar o restaurante
      const restaurant = await this.restaurantService.create({
        name: restaurantData.name,
        description: restaurantData.description,
        address: restaurantData.address,
        cep: restaurantData.cep,
        phone: restaurantData.phone,
        photo: restaurantData.photo.split(',')[1],
        rating: restaurantData.rating,
        category: categoryValue,
      });

      // Criar e salvar os produtos
      for (const productData of restaurantData.products) {
        const productDto = new CreateProductDto();
        productDto.name = productData.name;
        productDto.description = productData.description;
        productDto.price = productData.price;
        productDto.photo = productData.photo.split(',')[1];
        productDto.category = productData.category;
        productDto.restaurantId = restaurant.id;

        await this.productService.create(productDto);
      }
    }
  }
}
