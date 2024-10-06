import styled from '@emotion/styled';
import Typhography from '../common/Typography';
import theme from '../../styles/theme';
import Textfield from '../common/Textfield';
import Logo7 from '../../assets/images/logo7.png';
import Button from '../common/Button';
import { BookCover } from './BookCoverImage';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { useMutation } from '@tanstack/react-query';
import { postStoryBook } from '../../apis/book';
import useChildStore from '../../stores/useChlidStore';

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 0 20px;
`;

const TitleWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
  max-width: 500px;
  align-items: center;
  padding: 30px 10px 10px;
  margin: 0 auto;
`;

const Title = styled.div`
  display: flex;
  align-items: flex-end;
`;

const TitleImg = styled.img`
  width: 40px;
  height: 35px;
  margin-right: 10px;
`;

const InputWrapper = styled.div`
  display: flex;
  flex-direction: column;
  width: calc(100% - 60px);
  max-width: 470px;
  gap: 20px;
  background-color: ${theme.color.primary[50]};
  border-radius: 20px;
  padding: 20px 30px 30px;
  margin: 20px auto;
`;

const PeriodField = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;

  @media (min-width: 530px) {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    gap: 30px;
  }
`;

const CoverWrapper = styled.div`
  display: flex;
  flex-direction: column;
  width: calc(100% - 30px);
  max-width: 500px;
  padding-top: 20px;
  padding-left: 30px;
  padding-bottom: 30px;
  background-color: ${theme.color.primary[50]};
  border-radius: 20px;
  margin: 10px auto;
`;

const SelectContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 500px;
  margin-right: 20px;
  height: 30px;
`;

const Carousel = styled.div`
  overflow-x: auto;
  display: flex;
  width: 100%;
  max-width: 500px;
  padding: 15px 0 20px;
  scroll-behavior: smooth;
  margin: 0 auto;

  &::-webkit-scrollbar {
    display: none;
  }
`;

const CoverContainer = styled.div`
  display: flex;
  perspective: 1000px;
  animation: slide 0.6s ease-in-out;
  width: 100%;

  @keyframes slide {
    from {
      transform: translateX(100%);
    }
    to {
      transform: translateX(0%);
    }
  }
`;

const CoverImg = styled.div<{ isSelected: boolean }>`
  width: 120px;
  height: 170px;
  background-color: ${theme.color.primary[50]};
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 14px 14px 10px rgba(0, 0, 0, 0.2);
  margin-right: 30px;
  cursor: pointer;
  display: ${({ isSelected }) => (isSelected ? 'flex' : 'none')};
  align-items: center;
  justify-content: center;
  transition: transform 0.5s ease, box-shadow 0.6s ease;
  transform-style: preserve-3d;
  position: relative;

  &:hover {
    box-shadow: 14px 14px 8px rgba(0, 0, 0, 0.3);
  }
`;

const Image = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
`;

const ButtonWrapper = styled.div`
  display: flex;
  justify-content: end;
  max-width: 500px;
  width: 100%;
  margin: 20px auto;
`;

const BookCreate = () => {
  const navigate = useNavigate();
  const [title, setTitle] = useState<string>('');
  const [startDate, setStartDate] = useState<string>('');
  const [endDate, setEndDate] = useState<string>('');
  const [coverImg, setCoverImg] = useState<string>('');
  const { childId } = useChildStore();

  const { mutate, isPending } = useMutation({
    mutationFn: postStoryBook,
    onSuccess: () => {
      setTimeout(() => {
        navigate('/book');
      }, 300);
    },
  });

  const BookCovers = coverImg
    ? BookCover.filter((cover) => cover === coverImg)
    : BookCover;

  const goBack = () => {
    navigate(-1);
  };

  const handleImageClick = (image: string) => {
    setCoverImg(image);
  };

  const handleResetClick = () => {
    setCoverImg('');
  };

  const createStoryBook = async () => {
    mutate({
      data: {
        childId: childId,
        title: title,
        startDate: startDate,
        endDate: endDate,
        coverImage: coverImg,
      },
    });
  };

  return (
    <Wrapper>
      <TitleWrapper>
        <Title>
          <TitleImg src={Logo7} />
          <Typhography size="lg" weight="bold" color="greyScale" shade="800">
            동화 생성
          </Typhography>
        </Title>
        <Button size="sm" color="secondary" onClick={goBack}>
          목록보기
        </Button>
      </TitleWrapper>

      <InputWrapper>
        <Typhography size="md" weight="bold" color="greyScale" shade="800">
          동화 정보
        </Typhography>
        <Textfield
          label="제목"
          size="md"
          color="tertiary"
          isColoredLabel={true}
          inputValue={title}
          setInputValue={setTitle}
          helpText={'*필수사항'}
        />
        <PeriodField>
          <Textfield
            label="시작일"
            size="md"
            color="primary"
            isColoredLabel={true}
            inputValue={startDate}
            setInputValue={setStartDate}
            helpText={'*필수사항'}
          />
          <Textfield
            label="종료일"
            size="md"
            color="primary"
            isColoredLabel={true}
            inputValue={endDate}
            setInputValue={setEndDate}
            helpText={'*필수사항'}
          />
        </PeriodField>
      </InputWrapper>

      <CoverWrapper>
        <SelectContainer>
          <Typhography size="md" weight="bold" color="greyScale" shade="800">
            표지 선택
          </Typhography>
          <Typhography size="xs" color="greyScale" shade="700">
            *필수사항
          </Typhography>
          {coverImg && (
            <Button size="sm" onClick={handleResetClick}>
              다시 선택
            </Button>
          )}
        </SelectContainer>
        <Carousel>
          {BookCovers.map((cover) => (
            <CoverContainer key={cover} onClick={() => handleImageClick(cover)}>
              <CoverImg isSelected={coverImg === '' || coverImg === cover}>
                <Image src={cover} />
              </CoverImg>
            </CoverContainer>
          ))}
        </Carousel>
      </CoverWrapper>

      <ButtonWrapper>
        <Button size="md" onClick={createStoryBook} disabled={isPending}>생성하기</Button>
      </ButtonWrapper>
    </Wrapper>
  );
};

export default BookCreate;
