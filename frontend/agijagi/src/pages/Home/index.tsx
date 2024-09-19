import { useState } from 'react';
import Textfield from '../../components/common/Textfield';
import { ValidationState } from '../../components/common/Textfield/Textfield.types';
import Typhography from '../../components/common/Typography';
import useModal from '../../hooks/useModal';

const Home = () => {
  const [inputValue, setInputValue] = useState('');

  const modal = useModal();

  function validateInput(input: string): ValidationState {
    if (input.trim() === '') {
      return 'normal';
    }
    if (input.length >= 2 && input.length <= 8) {
      return 'success';
    }
    return 'danger';
  }

  return (
    <>
      <Typhography size="6xl" weight="extraBold" shade="900" color="tertiary">
        Hello
      </Typhography>
      <button
        onClick={() =>
          modal.push({
            children: (
              <div
                style={{
                  width: '100vw',
                  height: '50vh',
                  backgroundColor: '#fff',
                }}
              >
                center
              </div>
            ),
          })
        }
      >
        중앙 모달
      </button>
      <button
        onClick={() =>
          modal.push({
            children: (
              <div
                style={{
                  width: '100vw',
                  height: '50vh',
                  backgroundColor: '#fff',
                }}
              >
                bottom
              </div>
            ),
            animation: 'bottom',
          })
        }
      >
        하단 모달
      </button>
      <Typhography>안녕하세요</Typhography>
      <Textfield
        label="이름"
        size="sm"
        color="secondary"
        isColoredLabel={true}
        inputValue={inputValue}
        setInputValue={setInputValue}
        validationFunction={validateInput}
        disabled={false}
        helpText={'*이름은 필수항목이에요.'}
        checkText={'올바르게 입력했어요'}
        warningText={'이름은 2~8자의 형식을 만족해야해요'}
      ></Textfield>
    </>
  );
};

export default Home;
