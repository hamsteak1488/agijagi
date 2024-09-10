import React, {useState} from 'react';
import styled from '@emotion/styled';
import BookItem from './BookItem';
import BookListModal from './BookListModal';
import Logo7 from '../../assets/images/logo7.png';
import CoverImg1 from '../../assets/bookcover/cover1.png';
import CoverImg2 from '../../assets/bookcover/cover2.png';
import CoverImg3 from '../../assets/bookcover/cover3.png';
import CoverImg4 from '../../assets/bookcover/cover4.png';
import CoverImg5 from '../../assets/bookcover/cover5.png';
import CoverImg6 from '../../assets/bookcover/cover6.png';


const Wrapper = styled.div`
  padding-top: 20px;
  background-color: #ffecb3;
  height: 100vh;
  display: flex;
  flex-direction: column;
  overflow: hidden;
`;

const CarouselWrapper = styled.div`
  overflow-x: auto;
  display: flex;
  padding-top: 20px;
  padding-bottom: 80px;
  padding-left: 30px;
  scroll-behavior: smooth;
  overflow-y: hidden;

  &::-webkit-scrollbar {
    display: none;
  }
`;

const Title = styled.div`
  display: flex;
  margin-top: 20px;
  margin-bottom: 10px;
  margin-left: 20px;
`;

const TitleText = styled.h2`
  margin-bottom: 0px;
`

const TitleImg = styled.img`
  width: 40px;
  height: 35px;
  margin-right: 10px;
`;

const ModalWrapper = styled.div`
  display: flex;
  width: 100%;
  height: 55vh;
  box-sizing: border-box;
`;


// 임의로 만든 책 목록 -> 추후 데이터로 받아야함
const books = [
  { id: 1, image: CoverImg1, title: "우리 아기 태어난지 2주차", start: "2024-07-02", end: "2024-07-16", page: 14 },
  { id: 2, image: CoverImg2, title: "우리 아기 한달 일기", start: "2024-07-02", end: "2024-07.31", page: 31 },
  { id: 3, image: CoverImg3, title: "우리 아기 태어난지 6주차", start: "2024-08-02", end: "2024-08-09", page: 10 },
  { id: 4, image: CoverImg4, title: "우리 아기 태어난지 8주차", start: "2024-08-25", end: "2024-08-31", page: 12 },
  { id: 5, image: CoverImg5, title: "우리 아기 태어난지 12주차", start: "2024-09-02", end: "2024-09-12", page: 14 },
  { id: 6, image: CoverImg6, title: "우리 아기 태어난지 24주차", start: "2024-12-02", end: "2024-12-16", page: 16 },
];

const BookCarousel = () => {

  return (
    <Wrapper>
      <Title>
        <TitleImg src={Logo7} />
        <TitleText>Story Book</TitleText>
      </Title>
      <CarouselWrapper>
        {books.map(book => (
          <BookItem key={book.id} id={book.id} image={book.image} />
        ))}
      </CarouselWrapper>

      <ModalWrapper>
        <BookListModal />
      </ModalWrapper>

    </Wrapper>
  );
};

export default BookCarousel;
