import { DataState } from "../enum/data-state";

export interface AppState<T> {
  dataState: DataState;
  data?: T;
  error?: string;
}
