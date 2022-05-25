import { IParceiro } from 'app/shared/model/parceiro.model';
import { IUsuario } from 'app/shared/model/usuario.model';
import { Estado } from 'app/shared/model/enumerations/estado.model';

export interface IEndereco {
  id?: number;
  cep?: string | null;
  logradouro?: string | null;
  numero?: number | null;
  complemento?: string | null;
  bairro?: string | null;
  cidade?: string | null;
  estado?: Estado | null;
  parceiro?: IParceiro | null;
  usuario?: IUsuario | null;
}

export const defaultValue: Readonly<IEndereco> = {};
