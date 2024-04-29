export class User {
  id: number;
  name: string;
  surname: string;
  username: string;
  email: string;
  createdAt: Date;

  constructor(id: number, name: string, surname: string, username: string, email: string, createdAt: Date) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.username = username;
    this.email = email;
    this.createdAt = createdAt;
  }
}
