%%cu
#include<stdio.h>
#include<stdlib.h>
#define size 10

__global__ void MatMul(int *a, int *b, int*c, int n)
{
    int tx = threadIdx.x;
    int ty = threadIdx.y;
    int result = 0;
      for( int j=0;j<size; j++)
      {
          int p = *(a + ty*size + j);
          int q = *(b + j*size + tx);
          result =  result + p*q;
      }
       c[ty*size + tx] = result;
}



int main()
{
    int *A,*B,*C;
    A = (int*)malloc(size * size * sizeof(int));
    B = (int*)malloc(size * size * sizeof(int));
    C = (int*)malloc(size * size * sizeof(int));
    
    
    for(int i=0; i<size;i++)
    {
        for(int j=0; j<size; j++)
        {
            *(A + i*size + j) = rand()%10;
            *(B + i*size + j) = rand()%10;
        }
    }
    
    
    int *AD, *BD, *CD;
    
    cudaMalloc(&AD, size*size*sizeof(int));
    cudaMalloc(&BD, size*size*sizeof(int));
    cudaMalloc(&CD, size*size*sizeof(int));
    
    cudaMemcpy(AD, A, size*size*sizeof(int), cudaMemcpyHostToDevice);
    cudaMemcpy(BD, B, size*size*sizeof(int), cudaMemcpyHostToDevice);
    
    MatMul<<<1,size*size>>>(AD, BD, CD, size);
    
    cudaMemcpy(C, CD, size*size*sizeof(int), cudaMemcpyDeviceToHost);
    
    
    
   printf("Matrix A: \n");
	for (int i = 0; i < size; i++)
	{
		for (int j = 0; j < size; j++)
		{
			printf("%d ", *(A + i*size + j));
		}
		printf("\n");
	}
	printf("\n");
	printf("Matrix B: \n");
	for (int i = 0; i < size; i++)
	{
		for (int j = 0; j < size; j++)
		{
			printf("%d ", *(B + i*size + j));
		}
		printf("\n");
	}
	printf("Product: \n");
	for (int i = 0; i < size; i++)
	{
		for (int j = 0; j < size; j++)
		{
			printf("%d ", *(C + i*size + j));
		}
		printf("\n");
	}
	printf("\n");
    
    
    cudaFree(AD);
    cudaFree(BD);
    cudaFree(CD);
    
    free(A);
    free(B);
    free(C);
    
    return 0;
}
