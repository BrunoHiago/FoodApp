import { Injectable } from '@nestjs/common';
import { CreateRestaurantDto } from './dto/create-restaurant.dto';
import { UpdateRestaurantDto } from './dto/update-restaurant.dto';
import { Restaurant } from './entities/restaurant.entity';
import { Repository } from 'typeorm';
import { InjectRepository } from '@nestjs/typeorm';
import { Category } from 'src/utils/enums';

@Injectable()
export class RestaurantService {
  constructor(
    @InjectRepository(Restaurant)
    private restaurantRepository: Repository<Restaurant>,
  ) {}

  async create(createRestaurantDto: CreateRestaurantDto): Promise<Restaurant> {
    return await this.restaurantRepository.save(createRestaurantDto);
  }

  async findAll(): Promise<Restaurant[]> {
    return await this.restaurantRepository.find();
  }

  async findOne(id: string): Promise<Restaurant> {
    return await this.restaurantRepository.findOne({ where: { id } });
  }

  async update(
    id: string,
    updateRestaurantDto: UpdateRestaurantDto,
  ): Promise<Restaurant> {
    await this.restaurantRepository.update(id, updateRestaurantDto);
    return await this.restaurantRepository.findOne({ where: { id } });
  }

  async remove(id: string): Promise<void> {
    await this.restaurantRepository.delete(id);
  }

  async findByCategory(category: Category): Promise<Restaurant[]> {
    return await this.restaurantRepository.find({ where: { category } });
  }
}
