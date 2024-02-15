import { Level } from "../enum/level.enum";

export interface Note {
  id: number;
  title: string;
  desription: string;
  level: Level;
  createdAt: Date;
}
