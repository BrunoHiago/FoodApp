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
    const { userId, restaurantId } = createFavoriteDto;

    // Verifica se a relação já existe
    const existingFavorite = await this.favoriteRepository.findOne({
      where: { user: { id: userId }, restaurant: { id: restaurantId } },
      relations: ['user', 'restaurant'], // Certifique-se de incluir as relações
    });

    if (existingFavorite) {
      // Se já existir, deleta a relação
      await this.favoriteRepository.remove(existingFavorite);
      return { message: 'Favorito removido com sucesso' };
    } else {
      // Se não existir, cria a relação
      const user = await this.userService.findOne(userId);
      const restaurant = await this.restaurantService.findOne(restaurantId);

      const newFavorite = this.favoriteRepository.create({
        user: { id: user.id }, // Passa apenas o ID
        restaurant: { id: restaurant.id }, // Passa apenas o ID
      });

      await this.favoriteRepository.save(newFavorite);
      return { message: 'Favorito adicionado com sucesso' };
    }
  }

  findAll() {
    return this.favoriteRepository.find();
  }

  findOne(id: string) {
    console.log(id);
    return this.favoriteRepository.findOne({ where: { id } });
  }

  async findFavoritesByUser(userId: string) {
    console.log(userId);
    const favorite = await this.favoriteRepository.find({
      relations: ['restaurant', 'user'],
      where: { user: { id: userId } },
    });
    return favorite.map((favorite) => favorite.restaurant);
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

  async remove(userId: string, restaurantId: string) {
    console.log('delete');
    const favorite = await this.favoriteRepository.findOne({
      relations: ['restaurant', 'user'],
      where: {
        user: { id: userId },
        restaurant: { id: restaurantId },
      },
    });
    return this.favoriteRepository.remove(favorite);
  }
}
