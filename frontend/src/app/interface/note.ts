import { Level } from "../enum/level.enum";

export interface Note {
  id: number;
  title: string;
  description: string;
  level: Level;
  createdAt: Date;
}
