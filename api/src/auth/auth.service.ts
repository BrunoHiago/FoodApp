import { Injectable, UnauthorizedException } from '@nestjs/common';
import { JwtService } from '@nestjs/jwt';
import { UsersService } from 'src/users/users.service';
import { SignInDto } from './dto/sign-in-dto';

@Injectable()
export class AuthService {
  constructor(
    private jwtService: JwtService,
    private userService: UsersService,
  ) {}

  async login(signInDto: SignInDto) {
    const user = await this.userService.findByEmail(signInDto.email);
    if (!user) {
      throw new UnauthorizedException(
        `There isn't any user with email: ${signInDto.email}`,
      );
    }

    if (!(await user.checkPassword(signInDto.password))) {
      throw new UnauthorizedException(
        `Wrong password for user with email: ${signInDto.email}`,
      );
    }

    const payload = { username: user.name, sub: user.id };

    return {
      access_token: this.jwtService.sign(payload),
      user: user,
    };
  }
}
