import { Note } from "./note";

export interface CustomHttpResponse {
  timeStamp: Date;
  statucCode: number;
  status: string;
  message?: string;
  reason?: string;
  developerMessage?: string;
  notes?: Note[];
}
