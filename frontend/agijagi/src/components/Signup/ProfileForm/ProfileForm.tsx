import { css } from '@emotion/react';
import styled from '@emotion/styled';
import theme from '../../../styles/theme';
import Button from '../../common/Button';
import Textfield from '../../common/Textfield';
import Typhography from '../../common/Typography';
import defaultImg from '../../../assets/images/adult.png';
import { ValidationState } from '../../common/Textfield/Textfield.types';
import PlusCircle from '@heroicons/react/24/outline/PlusCircleIcon';
import { useNavigate } from 'react-router-dom';
import { signUp } from '../../../apis/userApi';
import BackIcon from '@heroicons/react/24/outline/ChevronLeftIcon';
import useModal from '../../../hooks/useModal';

export const Container = styled.div<{ width: number; isNext: boolean }>(
  (props) => css`
    display: flex;
    flex-direction: column;
    align-items: center;
    max-width: 500px;
    width: 80%;
    margin: 0% 0% 18vh 0%;
    gap: 4vh;
    z-index: 10;
    transition: all 1s;
    transform: translate(${!props.isNext ? 0 : props.width * 1}px, 0);
  `
);

export const ModalBackground = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: ${theme.color.primary[50]};
  margin: 1rem;
  padding: 2.5rem 2rem 2rem 2rem;
  border-radius: 0.5rem;
  gap: 1rem;
`;

export const ProfileCircleWrapper = styled.label`
  display: flex;
  position: relative;
  width: 128px;
  height: 128px;
  border-radius: 50%;
  border: 2px solid ${theme.color.greyScale[400]};
  background-color: ${theme.color.greyScale[200]};
`;

export const ImgWrapper = styled.div`
  width: 100%;
  height: 100%;
  border-radius: 50%;
  overflow: hidden;
`;

export const ProfileImg = styled.img`
  width: 100%;
  height: 100%;
`;

export const ProfileContainer = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
`;

export const AddIconWrapper = styled.div`
  position: absolute;
  width: 28px;
  height: 28px;
  left: 80%;
  top: 7%;
`;

export const InvisibleInput = styled.input`
  display: none;
`;

export const BackButton = styled.div(
  () => css`
    position: absolute;
    width: 24px;
    height: 24px;
    padding: 0.2rem;
    color: #fff;
    background-color: ${theme.color.primary[300]};
    border-radius: 50%;
    transition: all 0.75s;
    right: 120%;
    top: 2%;
    z-index: 1;
    box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px;
  `
);

interface ProfileForm {
  email: string;
  password: string;
  isNext: boolean;
  uploadImg: string;
  handleNext: () => void;
  handleUpload: (e: React.ChangeEvent<HTMLInputElement>) => void;
  isValidated: Record<string, boolean>;
  nickname: string;
  setNickname: React.Dispatch<React.SetStateAction<string>>;
  validationNickname: (e: string) => ValidationState;
}

export const ProfileForm = ({
  email,
  password,
  isNext,
  uploadImg,
  handleNext,
  handleUpload,
  isValidated,
  nickname,
  setNickname,
  validationNickname,
}: ProfileForm) => {
  const width = window.innerWidth;
  const navigator = useNavigate();

  const modal = useModal();

  const handleSignup = () => {
    signUp({
      email: email,
      password: password,
      nickname: nickname,
    })
      .then((response) => {
        console.log(response);
        modal.push({
          children: (
            <ModalBackground>
              <Typhography>회원가입이 완료되었어요!</Typhography>
              <Button onClick={modal.pop}>닫기</Button>
            </ModalBackground>
          ),
          onClose: () => navigator('/welcome'),
        });
      })
      .catch((error) => {
        modal.push({
          children: (
            <ModalBackground>
              {error.response.data.message}
              <Button onClick={modal.pop}>닫기</Button>
            </ModalBackground>
          ),
        });
      });
  };

  return (
    <Container width={width} isNext={!isNext}>
      <ProfileContainer>
        <Typhography size="6xl" weight="bold">
          프로필 등록
        </Typhography>
        <BackButton onClick={handleNext}>
          <BackIcon />
        </BackButton>
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
          handleSignup();
        }}
      >
        등록
      </Button>
    </Container>
  );
};
