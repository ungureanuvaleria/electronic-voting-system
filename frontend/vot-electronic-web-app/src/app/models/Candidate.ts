import {PoliticalParty} from './PoliticalParty';

export interface Candidate {
  id: number;
  candidateName: string;
  candidateSurname: string;
  candidateImgUrl: string;
  politicalParty: PoliticalParty;
}
