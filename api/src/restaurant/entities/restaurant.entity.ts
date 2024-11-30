import { Favorite } from 'src/favorite/entities/favorite.entity';
import { Product } from 'src/product/entities/product.entity';
import { Category } from 'src/utils/enums';
import {
  Column,
  CreateDateColumn,
  Entity,
  OneToMany,
  PrimaryGeneratedColumn,
  UpdateDateColumn,
} from 'typeorm';

@Entity()
export class Restaurant {
  @PrimaryGeneratedColumn('uuid')
  id: string;

  @Column()
  name: string;

  @Column()
  description: string;

  @Column()
  address: string;

  @Column()
  cep: string;

  @Column()
  phone: string;

  @Column()
  photo: string;

  @Column({ type: 'decimal' })
  rating: number;

  @Column({
    type: 'enum',
    enum: Category,
  })
  category: Category;

  @CreateDateColumn()
  createdAt: Date;

  @UpdateDateColumn()
  updatedAt: Date;

  @OneToMany(() => Product, (product) => product.restaurant)
  products: Product[];

  @OneToMany(() => Favorite, (favorite) => favorite.restaurant)
  favorites: Favorite[];
}
