import { IKit } from 'app/shared/model/kit.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IBateria {
  id?: number;
  numeroSerie?: number | null;
  status?: Status | null;
  carga?: number | null;
  kit?: IKit | null;
}

export const defaultValue: Readonly<IBateria> = {};
