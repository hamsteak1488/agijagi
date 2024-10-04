import styled from '@emotion/styled';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { addChild } from '../../../apis/childApi';
import { ValidationState } from '../../../components/common/Textfield/Textfield.types';
import { FirstBabyForm } from '../../../components/Signup/BabyInfo/FirstBabyForm/FirstBabyForm';
import {
  Relationship,
  SecondBabyForm,
} from '../../../components/Signup/BabyInfo/SecondBabyForm/SecondBabyForm';
import theme from '../../../styles/theme';
import { BabyGender } from '../../../types/user';

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
  const [babyNickname, setBabyNickname] = useState<string>('');
  const [birthday, setBirthday] = useState<string>('2024-09-27');
  const [selectedGender, setSelectedGender] = useState<BabyGender>('남아');
  const [selectedRelation, setSelectedRelation] =
    useState<Relationship>('엄마');
  const [weight, setWeight] = useState<string>('');
  const [height, setHeight] = useState<string>('');
  const [isValidated, setIsValidated] = useState<boolean>(false);
  const [isNext, setIsNext] = useState<boolean>(false);

  const navigator = useNavigate();

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

  const submitBaby = () => {
    const newChild = {
      name: babyName,
      nickname: '똘똘한놈',
      gender: selectedGender,
      birthday: birthday,
      image: uploadImg,
      birthHeight: height,
      birthWeight: weight,
    };
    console.log(newChild);
    addChild(newChild)
      .then((response) => {
        navigator('/main');
      })
      .catch((error) => {
        console.error(error);
      });
  };

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
        <SecondBabyForm
          isNext={isNext}
          selectedGender={selectedGender}
          selectedRelation={selectedRelation}
          setSelectedGender={setSelectedGender}
          setSelectedRelation={setSelectedRelation}
          weight={weight}
          setWeight={setWeight}
          height={height}
          setHeight={setHeight}
          submitBaby={submitBaby}
        />
      </Container>
    </Background>
  );
};
