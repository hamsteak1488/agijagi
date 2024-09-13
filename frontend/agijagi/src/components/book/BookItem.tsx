import React, { useState } from 'react';
import styled from '@emotion/styled';
import theme from '../../styles/theme';
import Typhography from '../common/Typography';
import { CalendarIcon } from '@heroicons/react/24/outline';

const CardContainer = styled.div`
  perspective: 1000px;
`;

const BookWrapper = styled.div<{ isFlipped: boolean; isLifted: boolean }>`
  width: 150px;
  height: 220px;
  background-color: ${theme.color.primary[50]};
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 14px 14px 10px rgba(0, 0, 0, 0.2);
  margin-right: 30px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: transform 0.5s ease, box-shadow 0.6s ease;
  transform-style: preserve-3d;
  position: relative;
  transform: ${({ isLifted, isFlipped }) => {
    if (isFlipped) return 'rotateY(180deg) translateY(-10px)';
    if (isLifted) return 'translateY(-10px)';
    return 'none';
  }};

  &:hover {
    box-shadow: 14px 14px 8px rgba(0, 0, 0, 0.3);
  }
`;

const BookBack = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  padding: 10px;
  overflow: hidden;
  transform: rotateY(180deg);
`;

const TitleLabel = styled.div`
  font-size: 12px;
  font-weight: bold;
  color: ${theme.color.greyScale[800]};
  margin-top: 10px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;

const PageLabel = styled.div`
  font-size: 12px;
  color: ${theme.color.greyScale[700]};
  margin: 10px 5px;
`;

const DateLabel = styled.div`
  background-color: #ffecb3;
  color: ${theme.color.greyScale[800]};
  max-width: 155px;
  display: flex;
  flex-direction: row;
  align-items: center;
  border-radius: 10px;
  padding: 5px 10px;
  margin-top: 30px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;

const CalendarImg = styled(CalendarIcon)`
  width: 14px;
  margin-right: 5px;
`;

const ImageWrapper = styled.div`
  display: flex;
  margin: 0 auto;
`;

const BookImage = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
`;

const BounceImage = styled.img`
  align-self: center;
  width: 220px;
  height: 220px;
  animation: bounce 2s infinite;

  @keyframes bounce {
    0%,
    20%,
    50%,
    80%,
    100% {
      transform: translateY(0);
    }
    40% {
      transform: translateY(-20px);
    }
    60% {
      transform: translateY(-10px);
    }
  }
`;

type BookItemProps = {
  book: BookProps | null;
  image: string;
};

interface BookProps {
  id: number;
  image: string;
  title: string;
  start: string;
  end: string;
  page: number;
}

const BookItem = ({ image, book }: BookItemProps) => {
  const [isFlipped, setIsFlipped] = useState(false);
  const [isLifted, setIsLifted] = useState(false);

  const handleCardClick = () => {
    if (!isLifted) {
      setIsLifted(true); // 먼저 카드를 위로 올림
      setTimeout(() => {
        setIsFlipped(true); // 그다음 카드가 뒤집힘
      }, 300); // 위로 올라간 후 0.3초 뒤에 뒤집힘
    } else {
      setIsFlipped(false); // 다시 클릭하면 초기 상태로
      setTimeout(() => {
        setIsLifted(false); // 0.3초 후에 내려옴
      }, 300);
    }
  };

  return (
    <>
      {book ? (
        <CardContainer onClick={handleCardClick}>
          <BookWrapper isFlipped={isFlipped} isLifted={isLifted}>
            {isFlipped && isLifted ? (
              <BookBack>
                <TitleLabel>{book.title}</TitleLabel>
                <PageLabel>{book.page} pages</PageLabel>
                <DateLabel>
                  <CalendarImg />
                  <Typhography size="2xs" color="greyScale" shade="800">
                    {book.start} ~ <br />
                    {book.end}
                  </Typhography>
                </DateLabel>
              </BookBack>
            ) : (
              <BookImage src={image} alt="Book Cover" />
            )}
          </BookWrapper>
        </CardContainer>
      ) : (
        <ImageWrapper>
          <BounceImage src={image} alt="No Book" />
        </ImageWrapper>
      )}
    </>
  );
};

export default BookItem;
