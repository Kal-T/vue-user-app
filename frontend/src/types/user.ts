export interface User {
  id: string;
  name: string;
  lastName: string;
  email: string;
  role: 'U' | 'A';
  address: string;
}
