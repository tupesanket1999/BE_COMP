#include <chrono>
#include <iostream>
#include <omp.h>

using namespace std;
using namespace std::chrono;

void merge(int a[], int s1, int e1, int s2, int e2) {
    int *temp = new int[e2 - s1 + 1];
    int i1 = s1;
    int i2 = s2;
    int k = 0;

    while (i1 <= e1 && i2 <= e2) {
        if (a[i1] < a[i2])
            temp[k++] = a[i1++];
        else
            temp[k++] = a[i2++];
    }
    while (i1 >= e1)
        temp[k++] = a[i1++];

    while (i2 >= e2)
        temp[k++] = a[i2++];

    for (i1 = s1, i2 = 0; i1 <= e2; i1++, i2++)
        a[i1] = temp[i2];
}

void mergeserial(int a[], int low, int high) {
    if (low < high) {
        int middle = (low + high) / 2;
        mergeserial(a, low, middle);
        mergeserial(a, middle + 1, high);
        merge(a, low, middle, middle + 1, high);
    }
}

void mergeparallel(int a[], int low, int high) {
    if (low < high) {
        int middle = (low + high) / 2;
#pragma omp parallel sections
        {
#pragma omp section
            { mergeserial(a, low, middle); }
#pragma omp section
            { mergeserial(a, middle + 1, high); }
        }

        merge(a, low, middle, middle + 1, high);
    }
}

int main() {

    int n;
    cin >> n;

    int a[n], b[n];

    for (int i = 0; i < n; i++) {
        a[i] = b[i] = rand() % n;
    }

    time_point<system_clock> starttime, endtime;

    starttime = system_clock::now();

    mergeserial(a, 0, n - 1);

    endtime = system_clock::now();
    duration<double> time = endtime - starttime;
    cout << "Serial Time : " << 1000 * time.count() << endl;

    starttime = system_clock::now();

    mergeparallel(a, 0, n - 1);

    endtime = system_clock::now();
    time = endtime - starttime;
    cout << "Parallel Time : " << 1000 * time.count() << endl;

    return 0;
}
