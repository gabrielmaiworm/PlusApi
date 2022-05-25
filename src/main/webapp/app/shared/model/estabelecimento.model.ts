import { IAvaliacao } from 'app/shared/model/avaliacao.model';

export interface IEstabelecimento {
  id?: number;
  endereco?: string | null;
  nome?: string | null;
  avaliacaos?: IAvaliacao[] | null;
}

export const defaultValue: Readonly<IEstabelecimento> = {};
