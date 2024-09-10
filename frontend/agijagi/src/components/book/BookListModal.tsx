import React, { useState } from 'react';
import styled from '@emotion/styled';
import { CalendarIcon } from '@heroicons/react/24/outline';
import CoverImg1 from '../../assets/bookcover/cover1.png';
import CoverImg2 from '../../assets/bookcover/cover2.png';
import CoverImg3 from '../../assets/bookcover/cover3.png';
import CoverImg4 from '../../assets/bookcover/cover4.png';
import CoverImg5 from '../../assets/bookcover/cover5.png';
import CoverImg6 from '../../assets/bookcover/cover6.png';

const ModalBox = styled.div`
  background-color: #fff;
  width: 100%;
  height: 100%;
  padding: 20px 0px;
  border-radius: 20px;
  box-shadow: 5px 8px 20px rgba(0, 0, 0, 0.2);
  text-align: center;
  display: flex;
  flex-direction: column;
`;

const FilterButton = styled.button<{ isActive: boolean }>`
  background-color: ${({ isActive }) => (isActive ? '#ffecb3' : '#f1f3f5')};
  border: none;
  border-radius: 20px;
  padding: 10px 20px;
  margin: 0 5px;
  font-size: 14px;
  cursor: pointer;
`;

const BookList = styled.div`
  display: flex;
  flex-direction: column;
  overflow-y: auto;
  padding-bottom: 40px;
  height: calc(100% - 60px); /* 필터 영역을 제외한 나머지 공간을 차지하게 설정 */
`;

const BookContainer = styled.div`
  display: flex;
  padding-left: 40px;
  padding-right: 30px;
  padding-top: 15px;
  padding-bottom: 10px;
`;

const BookImage = styled.img`
  min-width: 60px;
  height: 90px;
  border-radius: 10px;
  box-shadow: 5px 8px 20px rgba(0, 0, 0, 0.2);
`;

const ListContent = styled.div`
  display: flex;
  flex-direction: column;
  margin-left: 30px;
  text-align: left;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;

const TitleLabel = styled.div`
  font-size: 14px;
  margin-top: 5px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;

const PageLabel = styled.div`
  font-size: 10px;
  margin: 5px;
`;

const DateLabel = styled.div`
  background-color: #ffecb3;
  flex-direction: row;
  align-items: center;
  border-radius: 10px;
  padding: 5px 10px;
  font-size: 10px;
  margin-top: 15px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;

const CalendarImg = styled(CalendarIcon)`
  width: 12px;
  margin-right: 5px;
`;

// 임의로 만든 책 목록 -> 추후 데이터로 받아야함
const books = [
  { id: 1, image: CoverImg1, title: "우리 아기 태어난지 2주차", start: "2024-07-02", end: "2024-07-16", page: 14 },
  { id: 2, image: CoverImg2, title: "우리 아기 한달 일기", start: "2024-07-02", end: "2024-07-31", page: 31 },
  { id: 3, image: CoverImg3, title: "우리 아기 태어난지 6주주주주주주주주주주차", start: "2024-08-02", end: "2024-08-09", page: 10 },
  { id: 4, image: CoverImg4, title: "우리 아기 태어난지 8주차", start: "2024-08-25", end: "2024-08-31", page: 12 },
  { id: 5, image: CoverImg5, title: "우리 아기 태어난지 12주차", start: "2024-09-02", end: "2024-09-12", page: 14 },
  { id: 6, image: CoverImg6, title: "우리 아기 태어난지 24주차", start: "2024-12-02", end: "2024-12-16", page: 16 },
];

const Modal = () => {
  const [selectedFilter, setSelectedFilter] = useState('All');
  const filters = ['All', '1M', '3M', '6M', '1Y'];

  return (
    <ModalBox onClick={(e) => e.stopPropagation()}>
      <div style={{ display: 'flex', justifyContent: 'center', marginBottom: '20px' }}>
        {filters.map((filter) => (
          <FilterButton
            key={filter}
            isActive={selectedFilter === filter}
            onClick={() => setSelectedFilter(filter)}
          >
            {filter}
          </FilterButton>
        ))}
      </div>

      <BookList>
        {books.map(book => (
          <BookContainer key={book.id}>
            <BookImage src={book.image} alt={book.title} />

            <ListContent>
              <TitleLabel>{book.title}</TitleLabel>
              <PageLabel>{book.page} pages</PageLabel>
              <DateLabel>
                <CalendarImg />
                {book.start} ~ {book.end}
              </DateLabel>
            </ListContent>
          </BookContainer>
        ))}
      </BookList>
    </ModalBox>
  );
};

export default Modal;
