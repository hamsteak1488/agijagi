import React, { useState, useRef, useEffect } from 'react';
import styled from '@emotion/styled';
import theme from '../../styles/theme';
import BookFilter from './BookFilter';
import Typhography from '../../components/common/Typography';
import { CalendarIcon } from '@heroicons/react/24/outline';

const ModalBox = styled.div`
  background-color: #fff;
  width: 100%;
  height: 100%;
  padding: 10px 0;
  /* padding: 10px 0px; */
  border-top-left-radius: 20px;
  border-top-right-radius: 20px;
  box-shadow: 5px 8px 20px rgba(0, 0, 0, 0.2);
  text-align: center;
  display: flex;
  flex-direction: column;
`;

const FilterContainer = styled.div`
  display: flex;
  align-self: center;
  justify-content: center;
  width: 90%;
  border-bottom: 1px solid ${theme.color.greyScale[400]};
  border-width: 1px 60%;
`;

const BookList = styled.div`
  display: flex;
  flex-direction: column;
  overflow-y: auto;
  margin-top: 10px;
  padding-bottom: 80px;
  height: calc(100% - 50px);
`;

const BookContainer = styled.div`
  display: flex;
  padding-left: 40px;
  padding-right: 30px;
  padding-top: 15px;
  padding-bottom: 15px;
`;

const BookImage = styled.img`
  min-width: 60px;
  height: 90px;
  border-radius: 10px;
  box-shadow: 5px 8px 20px rgba(0, 0, 0, 0.2);
`;

const LabelContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin-left: 30px;
  text-align: left;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;

const TitleLabel = styled.div`
  font-size: ${theme.typography.fontSize.sm};
  font-weight: ${theme.typography.fontWeight.bold};
  color: ${theme.color.greyScale[800]};
  margin-top: 5px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;

const PageLabel = styled.div`
  font-size: ${theme.typography.fontSize.xs};
  color: ${theme.color.greyScale[700]};
  margin: 5px;
`;

const DateLabel = styled.div`
  background-color: #ffecb3;
  color: ${theme.color.greyScale[800]};
  font-weight: ${theme.typography.fontWeight.bold};
  width: 155px;
  display: flex;
  justify-content: center;
  flex-direction: row;
  align-items: center;
  border-radius: 10px;
  padding: 5px 10px;
  margin-top: 15px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;

const CalendarImg = styled(CalendarIcon)`
  width: 14px;
  margin-right: 5px;
`;

interface DateNavigationProps {
  year: number;
  month: number;
  handlePrev: () => void;
  handleNext: () => void;
}

interface BookProps {
  id: number;
  image: string;
  title: string;
  start: string;
  end: string;
  page: number;
}

interface BookListModalProps {
  onBookSelect: (book: BookProps | null) => void;
  filteredBooks: BookProps[];
  onScroll: (scrollPos: number) => void; // 스크롤 위치 전달하는 콜백 함수
}

type BookListProps = DateNavigationProps & BookListModalProps;

const BookListModal = ({
  year,
  month,
  handlePrev,
  handleNext,
  filteredBooks,
  onScroll,
  onBookSelect,
}: BookListProps) => {
  const listRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const handleScroll = () => {
      if (listRef.current) {
        onScroll(listRef.current.scrollTop); // 현재 스크롤 위치를 상위로 전달
      }
    };

    const ref = listRef.current;
    if (ref) {
      ref.addEventListener('scroll', handleScroll);
    }

    return () => {
      if (ref) {
        ref.removeEventListener('scroll', handleScroll);
      }
    };
  }, [onScroll]);

  return (
    <ModalBox>
      <FilterContainer>
        <BookFilter
          year={year}
          month={month}
          handlePrev={handlePrev}
          handleNext={handleNext}
        />
      </FilterContainer>

      <BookList ref={listRef}>
        {filteredBooks.length === 0 ? (
          <div style={{ marginTop: '30px' }}>
            <Typhography
              size="md"
              weight="regular"
              color="greyScale"
              shade="700"
            >
              이 달에 생성한 동화가 없습니다.
            </Typhography>
          </div>
        ) : (
          filteredBooks.map((book) => (
            <BookContainer key={book.id} onClick={() => onBookSelect(book)}>
              <BookImage src={book.image} alt={book.title} />

              <LabelContainer>
                <TitleLabel>{book.title}</TitleLabel>
                <PageLabel>{book.page} pages</PageLabel>
                <DateLabel>
                  <CalendarImg />
                  <Typhography
                    size="2xs"
                    color="greyScale"
                    shade="800"
                    weight="bold"
                  >
                    {book.start} ~ {book.end}
                  </Typhography>
                </DateLabel>
              </LabelContainer>
            </BookContainer>
          ))
        )}
      </BookList>
    </ModalBox>
  );
};

export default BookListModal;
