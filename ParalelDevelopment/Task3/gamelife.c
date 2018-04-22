#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>

/*
 * Create an array given the the length
 */
int *allocIntArray(int pLength)
{
    return malloc(sizeof(int)*(pLength*pLength));
}

/*
 * Fill the the array according to the value parameter, 
 * ,the length
 */
void fillArray(int *pArray, int pLenght, int pValue)
{
    int i=0;
    
    for(i=0; i < (pLenght*pLenght); i++)
    {
        pArray[i] = pValue;
    }
}

void printArray(int *pArray, int pLength)
{
    int i, j;
    
    for (i = 0; i<pLength; i++)
    {
        for(j=0; j<pLength; j++)
        {
            printf("%d ", pArray[pLength*i + j]);
        }
        printf("\n");
    }
    printf("\n\n");
}

int nextState(int *pArray,int i, int j,  int pLength)
{
    int up=0, upright=0, right=0, rightdown=0, down=0, downleft=0, left=0, leftup=0, sum=0, next=0;
    
    up = pArray[(i-1)*pLength + j];
    upright = pArray[(i-1)*pLength + j+1];
    right = pArray[i*pLength + j+1];
    rightdown = pArray[(i+1)*pLength + j+1];
    down = pArray[(i+1)*pLength + j];
    downleft = pArray[(i+1)*pLength + j-1];
    left = pArray[i*pLength + j-1];
    leftup = pArray[(i-1)*pLength + j-1];
    
    sum = up + upright + right + rightdown + down + downleft + left + leftup;
    
    if(sum==3)
    {
        next = 1;
    }
    else if((pArray[pLength*i + j]) == 1 && (sum<2 || sum>3))
    {
        next = 0;
    }
    
    return next;
}

int nextStateINJ(int *pArray,int i, int j,  int pLength)
{
    int up=0, upright=0, right=0, rightdown=0, down=0, downleft=0, left=0, leftup=0, sum=0, next=0;
    int inj = i*pLength + j;
    
    up = pArray[inj - pLength];
    upright = pArray[inj - pLength + 1];
    right = pArray[inj + 1];
    rightdown = pArray[inj + pLength + 1];
    down = pArray[inj + pLength];
    downleft = pArray[inj + pLength - 1];
    left = pArray[inj - 1];
    leftup = pArray[inj - pLength - 1];
    
    sum = up + upright + right + rightdown + down + downleft + left + leftup;
    
    if(sum==3)
    {
        next = 1;
    }
    else if((pArray[pLength*i + j]) == 1 && (sum<2 || sum>3))
    {
        next = 0;
    }
    
    return next;
}

void transferArray(int *pArrayA, int *pArrayB, int pLength)
{
    int i;
    
    for (i = 0; i<(pLength*pLength); i++)
    {
        *pArrayA++ = *pArrayB++;
    }
}

void generateNextPopulation(int *pBoard, int *pNextBoard, int pLength, int pGenerations)
{
    int i, j, g;
    
    printf("###  First Generation ### \n\n");
    printArray(pBoard, pLength);
    
    for(g=0; g<pGenerations; g++)
    {
        for (i=1; i < pLength-1; i++)
        {
            for (j=1; j< pLength-1; j++)
            {
                pNextBoard[pLength*i + j] = nextStateINJ(pBoard, i, j, pLength);
            }
        }
        
        printf("Generation Number #%d\n", g);
        printArray(pNextBoard, pLength);

        // Permutate the Boards, the new genaration becomes the actual
        transferArray(pBoard, pNextBoard, pLength);
        fillArray(pNextBoard, pLength, 0);
    }
}

void initArray(int *pBoard, int pLength)
{
    pBoard[1*pLength + 2] = 1;
    pBoard[2*pLength + 3] = 1;
    pBoard[3*pLength + 1] = 1;
    pBoard[3*pLength + 2] = 1;
    pBoard[3*pLength + 3] = 1;
}

void initRandomArray(int *pArray, int pLength)
{
    int i, j;
    
    for (i = 0; i<pLength; i++)
    {
        for(j=0; j<pLength; j++)
        {
            pArray[pLength*i + j] = random()%2;
        }
    }
}

int main(void)
{
    int *board, *nextBoard, length, generations;
    length  = 10;
    generations = 6;
    srandom(time(NULL));
    board = allocIntArray(length);
    nextBoard = allocIntArray(length);
    
    fillArray(board, length, 0);
    fillArray(nextBoard, length, 0);
    
    initArray(board, length);
    //initRandomArray(board, length);
    
    generateNextPopulation(board, nextBoard, length, generations);
    
    free(board);
    free(nextBoard);

}
