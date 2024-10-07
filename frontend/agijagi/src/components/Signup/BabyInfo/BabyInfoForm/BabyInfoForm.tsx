import { useState } from 'react';
import { css } from '@emotion/react';
import styled from '@emotion/styled';
import theme from '../../../../styles/theme';
import { ValidationState } from '../../../common/Textfield/Textfield.types';
import { FirstBabyForm } from '../FirstBabyForm/FirstBabyForm';
import { SecondBabyForm } from '../SecondBabyForm/SecondBabyForm';

export const Background = styled.div`
  width: 100%;
  height: 100%;
  background-color: ${theme.color.primary[50]};
`;

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  width: 100%;
  height: 100%;
`;

const today = new Date();

export const BabyInfoForm = () => {
  const [uploadImg, setUploadImg] = useState<File | null>(null);
  const [babyName, setBabyName] = useState<string>('');
  const [birthday, setBirthday] = useState<string>('2024-09-27');
  const [isValidated, setIsValidated] = useState<boolean>(false);
  const [isNext, setIsNext] = useState<boolean>(false);
  const handleUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      setUploadImg(file);
    }
  };

  function validateNickName(input: string): ValidationState {
    if (input.trim() === '') {
      return 'normal';
    }

    if (input.length >= 2 && input.length <= 8) {
      setIsValidated(true);
      return 'success';
    } else {
      setIsValidated(false);
      return 'danger';
    }
  }

  return (
    <Background>
      <Container>
        <FirstBabyForm
          isNext={isNext}
          uploadImg={uploadImg}
          isValidated={isValidated}
          handleUpload={handleUpload}
          babyName={babyName}
          setBabyName={setBabyName}
          birthday={birthday}
          validationNickname={validateNickName}
          setBirthday={setBirthday}
          setIsNext={setIsNext}
        />
        <SecondBabyForm isNext={isNext} />
      </Container>
    </Background>
  );
};
