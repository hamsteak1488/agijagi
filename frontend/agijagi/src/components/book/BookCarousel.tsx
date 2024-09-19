import React, { useState, useRef } from 'react';
import styled from '@emotion/styled';
import BookItem from './BookItem';
import BookListModal from './BookListModal';
import StoryBook from './StoryBook';
import Typhography from '../common/Typography';
import Logo7 from '../../assets/images/logo7.png';
import Logo2 from '../../assets/images/logo2.png';
// 임의로 책 표지 넣으려고 import
import CoverImg1 from '../../assets/bookcover/cover1.png';
import CoverImg2 from '../../assets/bookcover/cover2.png';
import CoverImg3 from '../../assets/bookcover/cover3.png';
import CoverImg4 from '../../assets/bookcover/cover4.png';
import CoverImg5 from '../../assets/bookcover/cover5.png';
import CoverImg6 from '../../assets/bookcover/cover7.png';
import CoverImg7 from '../../assets/bookcover/cover12.png';
import CoverImg8 from '../../assets/bookcover/cover11.png';

const Wrapper = styled.div`
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
  padding-bottom: 50px;
  padding-left: 30px;
  scroll-behavior: smooth;
  overflow-y: hidden;

  &::-webkit-scrollbar {
    display: none;
  }
`;

const ImageWrapper = styled.div`
  display: flex;
  padding-top: 20px;
  padding-bottom: 50px;
`;

const Title = styled.div`
  display: flex;
  align-items: flex-end;
  margin-top: 30px;
  margin-bottom: 10px;
  margin-left: 20px;
`;

const TitleImg = styled.img`
  width: 40px;
  height: 35px;
  margin-right: 10px;
`;

const ModalWrapper = styled.div`
  display: flex;
  width: 100%;
  height: 52vh;
  box-sizing: border-box;
`;

// 임의로 만든 책 목록 -> 추후 데이터로 받아야함
const books: BookProps[] = [
  {
    id: 1,
    image: CoverImg1,
    title: '우리 아기 태어난지 2주차',
    start: '2024-07-02',
    end: '2024-07-16',
    page: 7,
  },
  {
    id: 2,
    image: CoverImg2,
    title: '우리 아기 한달 일기',
    start: '2024-07-02',
    end: '2024-07-31',
    page: 15,
  },
  {
    id: 3,
    image: CoverImg3,
    title: '우리 아기 태어난지 6주주주주주주차',
    start: '2024-07-02',
    end: '2024-08-09',
    page: 10,
  },
  {
    id: 4,
    image: CoverImg4,
    title: '우리 아기 태어난지 8주차',
    start: '2024-07-25',
    end: '2024-08-26',
    page: 12,
  },
  {
    id: 5,
    image: CoverImg5,
    title: '우리 아기 태어난지 9주차',
    start: '2024-07-02',
    end: '2024-09-07',
    page: 20,
  },
  {
    id: 6,
    image: CoverImg6,
    title: '우리 아기 태어난지 10주차',
    start: '2024-07-02',
    end: '2024-09-16',
    page: 30,
  },
  {
    id: 7,
    image: CoverImg7,
    title: '우리 아기 태어난지 11주차',
    start: '2024-07-02',
    end: '2024-09-23',
    page: 40,
  },
  {
    id: 8,
    image: CoverImg8,
    title: '우리 아기 태어난지 12주차',
    start: '2024-07-02',
    end: '2024-09-30',
    page: 50,
  },
];

interface BookProps {
  id: number;
  image: string;
  title: string;
  start: string;
  end: string;
  page: number;
}

const today = new Date();
const todayYear = today.getFullYear();
const todayMonth = today.getMonth() + 1;

const BookCarousel = () => {
  const [year, setYear] = useState(todayYear);
  const [month, setMonth] = useState(todayMonth);
  const [selectedBook, setSelectedBook] = useState<BookProps | null>(null);
  const [scrollPosition, setScrollPosition] = useState(0);
  const carouselRef = useRef<HTMLDivElement>(null);
  const listRef = useRef<HTMLDivElement>(null);

  // 책 클릭 시 호출되는 함수
  const handleBookSelect = (book: BookProps | null) => {
    // 책 한권이 선택되면 나머지 책들은 안보이게 함
    if (selectedBook && book && selectedBook.id === book.id) {
      setSelectedBook(null);
    } else {
      setSelectedBook(book);
    }
  };

  // 뒤로가기
  const goBack = () => {
    setSelectedBook(null);
  };

   // Carousel에서 스크롤될 때 동작하는 함수
  const handleCarouselScroll = () => {
    if (carouselRef.current) {
      const scrollLeft = carouselRef.current.scrollLeft;
      setScrollPosition(scrollLeft); // 현재 Carousel의 스크롤 위치 저장
      if (listRef.current) {
        listRef.current.scrollTop = scrollLeft / 1.7; // 스크롤 동기화 (비율 조정)
      }
    }
  };

  // 스크롤 위치를 업데이트하는 함수
  const handleListScroll = (scrollPos: number) => {
    if (carouselRef.current) {
      const scrollRatio = 1.7; // 스크롤 비율 조정
      carouselRef.current.scrollLeft = scrollPos * scrollRatio;
    }
  };

  const handlePrev = () => {
    setMonth((prevMonth) => {
      if (prevMonth === 1) {
        // 1월이면 작년 12월로
        setYear((prevYear) => prevYear - 1);
        return 12;
      } else {
        return prevMonth - 1;
      }
    });
  };

  const handleNext = () => {
    setMonth((prevMonth) => {
      if (prevMonth === 12) {
        // 12월이면 다음 해 1월로
        setYear((prevYear) => prevYear + 1);
        return 1;
      } else {
        return prevMonth + 1;
      }
    });
  };

  // 동화책 마지막 날짜의 년과 달을 기준으로 필터링
  const filteredBooks = books.filter((book) => {
    const startDate = new Date(book.end); // 문자열을 Date 객체로 변환
    const startMonth = startDate.getMonth() + 1;
    const startYear = startDate.getFullYear();
    return startMonth === month && startYear === year;
  });

  return (
    <Wrapper>
      <Title>
        <TitleImg src={Logo7} />
        <Typhography size="lg" weight="bold" color="greyScale" shade="800">
          Story Book
        </Typhography>
      </Title>

      {filteredBooks.length === 0 ? (
        <ImageWrapper>
          <BookItem image={Logo2} book={null} onBookSelect={handleBookSelect} isSelected={false}/>
        </ImageWrapper>
      ) : (
        <CarouselWrapper ref={carouselRef} onScroll={handleCarouselScroll}>
          {filteredBooks.map((book) => (
             // 선택된 책이 없으면 모든 책을 보여주고, 선택된 책이 있으면 해당 책만 보여줌
            (selectedBook === null || selectedBook.id === book.id) && (
              <BookItem
                key={book.id}
                image={book.image}
                book={book}
                onBookSelect={handleBookSelect}
                isSelected={selectedBook?.id === book.id}
              />
            )
          ))}
        </CarouselWrapper>
      )}

      <ModalWrapper>
        {selectedBook ? (
          <StoryBook book={selectedBook} goBack={goBack} />
        ) : (
          <BookListModal
            year={year}
            month={month}
            handleNext={handleNext}
            handlePrev={handlePrev}
            filteredBooks={filteredBooks}
            onScroll={handleListScroll}
            onBookSelect={handleBookSelect}
          />
        )}
      </ModalWrapper>
    </Wrapper>
  );
};

export default BookCarousel;
