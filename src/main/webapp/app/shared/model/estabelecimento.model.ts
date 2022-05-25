import { IAvaliacao } from 'app/shared/model/avaliacao.model';

export interface IEstabelecimento {
  id?: number;
  endereco?: string | null;
  nome?: string | null;
  telefone?: string | null;
  categoria?: string | null;
  notaMedia?: string | null;
  localizacao?: string | null;
  imagemContentType?: string | null;
  imagem?: string | null;
  precoFilter?: number | null;
  placeId?: string | null;
  avaliacaos?: IAvaliacao[] | null;
}

export const defaultValue: Readonly<IEstabelecimento> = {};
