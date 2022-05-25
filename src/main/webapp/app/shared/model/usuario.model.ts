import dayjs from 'dayjs';
import { IEndereco } from 'app/shared/model/endereco.model';
import { IUser } from 'app/shared/model/user.model';
import { Lesao } from 'app/shared/model/enumerations/lesao.model';
import { Situacao } from 'app/shared/model/enumerations/situacao.model';
import { Uso } from 'app/shared/model/enumerations/uso.model';

export interface IUsuario {
  id?: number;
  nome?: string | null;
  sobrenome?: string | null;
  nascimento?: string | null;
  documento?: number | null;
  lesao?: Lesao | null;
  situacao?: Situacao | null;
  uso?: Uso | null;
  nomeDependente?: string | null;
  nascimentoDependente?: string | null;
  telefone?: string | null;
  facebook?: string | null;
  instagram?: string | null;
  termo?: boolean | null;
  treinamento?: boolean | null;
  fotoDocContentType?: string | null;
  fotoDoc?: string | null;
  fotoComDocContentType?: string | null;
  fotoComDoc?: string | null;
  endereco?: IEndereco | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IUsuario> = {
  termo: false,
  treinamento: false,
};
