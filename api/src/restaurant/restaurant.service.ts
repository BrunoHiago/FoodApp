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

  async findAll(userId: string) {
    const restaurants = await this.restaurantRepository.find({
      relations: ['favorites', 'favorites.user'],
    });

    console.log(restaurants);

    return restaurants
      .map((restaurant) => {
        return {
          ...restaurant,
          favorite: restaurant.favorites
            .map((favorite) => favorite.user.id)
            .includes(userId),
        };
      })
      .sort((a, b) => {
        return a.rating > b.rating ? -1 : 1;
      });
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
