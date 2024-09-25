import styled from '@emotion/styled';
import { css } from '@emotion/react';
import theme from '../../styles/theme';
import { useState } from 'react';
import Textfield from '../../components/common/Textfield';
import Button from '../../components/common/Button';
import Typhography from '../../components/common/Typography';
import { ValidationState } from '../../components/common/Textfield/Textfield.types';
import defaultImg from '../../assets/images/adult.png';

export const Container = styled.div`
  width: 100%;
  height: 100%;
  background-color: ${theme.color.primary[50]};
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: end;
`;

export const FormContainer = styled.div<{ width: number; isNext: boolean }>(
  (props) => css`
    position: absolute;
    display: flex;
    flex-direction: column;
    align-items: center;
    max-width: 500px;
    width: 80%;
    margin: 0% 0% 30vh 0%;
    gap: 42px;
    z-index: 10;
    transition: all 1s;
    transform: translate(${!props.isNext ? 0 : props.width * -1}px, 0);
  `
);

export const ProfileForm = styled.div<{ width: number; isNext: boolean }>(
  (props) => css`
    position: absolute;
    display: flex;
    flex-direction: column;
    align-items: center;
    max-width: 500px;
    width: 80%;
    margin: 0% 0% 30vh 0%;
    gap: 42px;
    z-index: 10;
    transition: all 1s;
    transform: translate(${!props.isNext ? 0 : props.width * 1}px, 0);
  `
);

export const ImgWrapper = styled.div`
  width: 128px;
  height: 128px;
  padding: 0.5rem;
  border-radius: 50%;
  border: 2px solid ${theme.color.greyScale[400]};
  background-color: ${theme.color.greyScale[200]};
  overflow-y: hidden;
`;

export const ProfileImg = styled.img`
  width: 100%;
  height: 100%;
`;

export const ProfileContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
`;

export const Signup = () => {
  const [email, setEmail] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [nickname, setNickname] = useState<string>('');
  const [isValidated, setIsValidated] = useState<boolean[]>([false, false]);
  const [isNext, setIsNext] = useState<boolean>(false);
  const [uploadImg, setUploadImg] = useState<string>('');
  const width = window.innerWidth;

  function validatePassword(input: string): ValidationState {
    if (input.trim() === '') {
      return 'normal';
    }

    if (input.length >= 8 && input.length <= 16) {
      setIsValidated([isValidated[0], true]);
      return 'success';
    } else {
      setIsValidated([isValidated[0], false]);
      return 'danger';
    }
  }

  function validateEmail(input: string): ValidationState {
    if (input.trim() === '') {
      return 'normal';
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (emailRegex.test(input)) {
      setIsValidated([true, isValidated[1]]);
      return 'success';
    } else {
      setIsValidated([false, isValidated[1]]);
      return 'danger';
    }
  }

  return (
    <Container>
      <FormContainer width={width} isNext={isNext}>
        <Typhography size="7xl" weight="bold">
          회원가입
        </Typhography>
        <Textfield
          size="lg"
          label="이메일"
          color="secondary"
          isColoredLabel={true}
          fullWidth={true}
          inputValue={email}
          checkText={'올바르게 입력했어요'}
          warningText={'이메일 형식이 일치하지 않아요'}
          disabled={isNext}
          setInputValue={setEmail}
          validationFunction={validateEmail}
        ></Textfield>
        <Textfield
          size="lg"
          label="비밀번호"
          isColoredLabel={true}
          fullWidth={true}
          inputValue={password}
          setInputValue={setPassword}
          helpText={'* 8~16자의 영소문자와 숫자로 이루어져야 해요.'}
          checkText={'올바르게 입력했어요'}
          warningText={'비밀번호 형식이 일치하지 않아요'}
          validationFunction={validatePassword}
          disabled={isNext}
          type="password"
        ></Textfield>
        <Button
          size="md"
          color={isValidated[0] && isValidated[1] ? 'primary' : 'greyScale'}
          style={{ marginTop: '2rem', transition: 'all 0.75s' }}
          fullWidth={true}
          disabled={!(isValidated[0] && isValidated[1])}
          onClick={() => {
            setIsNext(true);
          }}
        >
          다음
        </Button>
      </FormContainer>
      <ProfileForm width={width} isNext={!isNext}>
        <ProfileContainer>
          <ImgWrapper>
            <ProfileImg src={uploadImg ? uploadImg : defaultImg} />
          </ImgWrapper>
          <Typhography size="xl" weight="bold">
            사진
          </Typhography>
        </ProfileContainer>
        <Textfield
          size="lg"
          isColoredLabel={true}
          inputValue={nickname}
          setInputValue={setNickname}
          label="닉네임"
        />
        <Button
          size="md"
          color={isValidated[0] && isValidated[1] ? 'primary' : 'greyScale'}
          style={{ marginTop: '2rem' }}
          fullWidth={true}
          disabled={!(isValidated[0] && isValidated[1])}
          onClick={() => {
            setIsNext(!isNext);
          }}
        >
          등록
        </Button>
      </ProfileForm>
    </Container>
  );
};

export default Signup;
