import { Injectable } from '@nestjs/common';
import { CreateProductDto } from './dto/create-product.dto';
import { UpdateProductDto } from './dto/update-product.dto';
import { Category } from 'src/utils/enums';
import { Product } from './entities/product.entity';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { RestaurantService } from 'src/restaurant/restaurant.service';

@Injectable()
export class ProductService {
  constructor(
    @InjectRepository(Product)
    private productRepository: Repository<Product>,
    private restaurantService: RestaurantService,
  ) {}

  async create(createProductDto: CreateProductDto): Promise<Product> {
    return await this.productRepository.save({
      ...createProductDto,
      restaurant: await this.restaurantService.findOne(
        createProductDto.restaurantId,
      ),
    });
  }

  async findAll(): Promise<Product[]> {
    const products = await this.productRepository.find({
      relations: ['restaurant'],
    });

    return products.map((product) => {
      return {
        ...product,
        restaurantId: product.restaurant.id,
      };
    });
  }

  async findOne(id: string): Promise<Product> {
    return await this.productRepository.findOne({ where: { id } });
  }

  async update(
    id: string,
    updateProductDto: UpdateProductDto,
  ): Promise<Product> {
    await this.productRepository.update(id, updateProductDto);
    return await this.productRepository.findOne({ where: { id } });
  }

  async remove(id: string): Promise<void> {
    await this.productRepository.delete(id);
  }

  async findByCategory(category: Category): Promise<Product[]> {
    return await this.productRepository.find({ where: { category } });
  }
}
