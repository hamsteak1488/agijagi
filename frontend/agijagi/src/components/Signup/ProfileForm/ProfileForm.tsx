import Button from '../../common/Button';
import Textfield from '../../common/Textfield';
import Typhography from '../../common/Typography';
import defaultImg from '../../../assets/images/adult.png';
import { ValidationState } from '../../common/Textfield/Textfield.types';
import PlusCircle from '@heroicons/react/24/outline/PlusCircleIcon';
import { useNavigate } from 'react-router-dom';
import {
  AddIconWrapper,
  Container,
  ImgWrapper,
  InvisibleInput,
  ProfileCircleWrapper,
  ProfileContainer,
  ProfileImg,
} from './ProfileForm.styles';

interface ProfileForm {
  isNext: boolean;
  uploadImg: string;
  handleUpload: (e: React.ChangeEvent<HTMLInputElement>) => void;
  isValidated: Record<string, boolean>;
  nickname: string;
  setNickname: React.Dispatch<React.SetStateAction<string>>;
  validationNickname: (e: string) => ValidationState;
}

export const ProfileForm = ({
  isNext,
  uploadImg,
  handleUpload,
  isValidated,
  nickname,
  setNickname,
  validationNickname,
}: ProfileForm) => {
  const width = window.innerWidth;
  const navigator = useNavigate();

  return (
    <Container width={width} isNext={!isNext}>
      <ProfileContainer>
        <Typhography size="6xl" weight="bold">
          프로필 등록
        </Typhography>
        <div></div>
        <ProfileCircleWrapper htmlFor="file">
          <AddIconWrapper>
            <PlusCircle fill="#fff" />
          </AddIconWrapper>
          <ImgWrapper>
            <ProfileImg src={uploadImg ? uploadImg : defaultImg} />
          </ImgWrapper>
          <InvisibleInput
            type="file"
            multiple
            id="file"
            onChange={handleUpload}
          />
        </ProfileCircleWrapper>
        <Typhography size="xl" weight="bold">
          프로필
        </Typhography>
      </ProfileContainer>
      <Textfield
        label="닉네임"
        size="lg"
        isColoredLabel={true}
        inputValue={nickname}
        setInputValue={setNickname}
        validationFunction={validationNickname}
        helpText={'닉네임은 2~8자의 규칙을 만족해야해요'}
        checkText={'올바르게 입력했어요'}
        warningText={'닉네임 형식이 일치하지 않아요'}
      />
      <Button
        size="md"
        color={isValidated['nickname'] ? 'primary' : 'greyScale'}
        style={{ marginTop: '2rem' }}
        fullWidth={true}
        disabled={!isValidated['nickname']}
        onClick={() => {
          navigator('/welcome');
        }}
      >
        등록
      </Button>
    </Container>
  );
};
