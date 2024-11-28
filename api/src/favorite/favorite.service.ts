import { Injectable } from '@nestjs/common';
import { CreateFavoriteDto } from './dto/create-favorite.dto';
import { UpdateFavoriteDto } from './dto/update-favorite.dto';
import { Favorite } from './entities/favorite.entity';
import { Repository } from 'typeorm';
import { InjectRepository } from '@nestjs/typeorm';
import { RestaurantService } from 'src/restaurant/restaurant.service';
import { UsersService } from 'src/users/users.service';

@Injectable()
export class FavoriteService {
  constructor(
    @InjectRepository(Favorite)
    private favoriteRepository: Repository<Favorite>,
    private userService: UsersService,
    private restaurantService: RestaurantService,
  ) {}

  async create(createFavoriteDto: CreateFavoriteDto) {
    const favorite = this.favoriteRepository.create({
      user: await this.userService.findOne(createFavoriteDto.userId),
      restaurant: await this.restaurantService.findOne(
        createFavoriteDto.restaurantId,
      ),
    });
    return this.favoriteRepository.save(favorite);
  }

  findAll() {
    return this.favoriteRepository.find();
  }

  findOne(id: string) {
    return this.favoriteRepository.findOne({ where: { id } });
  }

  async update(id: string, updateFavoriteDto: UpdateFavoriteDto) {
    const favorite = await this.favoriteRepository.preload({
      id: id,
      user: await this.userService.findOne(updateFavoriteDto.userId),
      restaurant: await this.restaurantService.findOne(
        updateFavoriteDto.restaurantId,
      ),
    });
    return this.favoriteRepository.save(favorite);
  }

  async remove(id: string) {
    return this.favoriteRepository.delete(id);
  }
}
