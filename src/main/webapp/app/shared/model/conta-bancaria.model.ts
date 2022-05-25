import { IParceiro } from 'app/shared/model/parceiro.model';
import { Tipo } from 'app/shared/model/enumerations/tipo.model';

export interface IContaBancaria {
  id?: number;
  banco?: string | null;
  agencia?: number | null;
  numeroConta?: number | null;
  tipo?: Tipo | null;
  parceiro?: IParceiro | null;
}

export const defaultValue: Readonly<IContaBancaria> = {};
